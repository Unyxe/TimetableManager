import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class main {
    public static void main(String[] args) {
        Student[] students = new Student[45];
        for(int i = 0; i < students.length;i++){
            students[i] = Student.randomStudent();
        }
        ArrayList<Student>[][] classStudentList =  TimetableManager.putStudentsIntoClasses(students);
        for(int i = 0; i < students.length;i++){
            System.out.println(students[i].name);
            for(int j = 0; j < students[i].subjectsTaken.length;j++){
                System.out.println(students[i].subjectsTaken[j] + " " + students[i].classIDS[j]);
            }
        }
        System.out.println(TimetableManager.OptimisationFitnessFunction(students, classStudentList));
        TimetableManager.OptimiseStudents(students, classStudentList, 500, 1000,20,  true);
        System.out.println("Class stats: ");
        for(int i = 0; i < classStudentList.length;i++){
            for(int j = 0; j < classStudentList[i].length;j++){
                System.out.println(classStudentList[i][j].size());
            }
        }

        int[][][] simLessons = TimetableManager.findSimLessonsRecursively(classStudentList, new ArrayList<int[]>());
//        for(int i = 0; i < simLessons.length;i++){
//            String line = "";
//            for(int j = 0;j < simLessons[i].length;j++){
//                line += "[" + simLessons[i][j][0] + ", " + simLessons[i][j][1] + "]";
//            }
//            System.out.println(line);
//        }
        System.out.println(simLessons.length + " SimLessons found!");
        System.out.println(Arrays.deepToString(simLessons[0]));
        int[][][] solution = TimetableManager.findTimetableSolutions(simLessons, true);
        if(solution == null){
            System.out.println("Solution search failed.");
        } else{
            System.out.println("Solution found!");
            for(int i = 0; i < solution.length;i++){
                String line = (i+1) + ": ";
                for(int j = 0;j < solution[i].length;j++){
                    line += "[" + solution[i][j][0] + ", " + solution[i][j][1] + "]";
                }
                System.out.println(line);
            }
            System.out.println(solution.length);

            //TimetableManager.OptimiseStudents(students, classStudentList, 100, 100,20, true, true);
        }

    }
}

class Student{
    public String name;
    public int[] subjectsTaken;
    public int[] classIDS;
    public int[] subjectMapping = new int[TimetableManager.classN.length];
    static Random rn = new Random();

    public Student(String name, int[] subjectsTaken){
        this.name = name;
        this.subjectsTaken = subjectsTaken;
        classIDS = new int[subjectsTaken.length];
        for(int i = 0; i < subjectsTaken.length;i++){
            subjectMapping[subjectsTaken[i]] = i;
        }
    }

    public static Student randomStudent(){
        String name = "M_FM";
        boolean doPhysics = rn.nextDouble()<0.7;
        boolean doChemistry = false;
        boolean doComputerScience = false;
        boolean doBiology = false;
        ArrayList<Integer> subjectList = new ArrayList<>();
        subjectList.add(0);
        subjectList.add(1);
        if(doPhysics){
            doChemistry = rn.nextDouble()<0.3;
            if(!doChemistry){
                doComputerScience = rn.nextDouble()<0.5;
            }
        }else{
            doChemistry = rn.nextDouble()<0.5;
            if(doChemistry){
                doBiology = rn.nextDouble()<0.2;
                if(!doBiology){
                    doComputerScience = rn.nextDouble()<0.5;
                }
            }else{
                doComputerScience = rn.nextDouble()<0.8;
                if(!doComputerScience){
                    doBiology=true;
                }
            }
        }
        if(doPhysics){
            subjectList.add(2);
            name += "_P";
        }
        if(doComputerScience){
            subjectList.add(3);
            name += "_CS";
        }
        if(doChemistry){
            subjectList.add(4);
            name += "_C";
        }
        if(doBiology){
            subjectList.add(5);
            name += "_B";
        }
        int[] subjects = new int[subjectList.size()];
        for(int i = 0;i < subjects.length;i++){
            subjects[i] = subjectList.get(i);
        }
        return new Student(name, subjects);
    }
}
class TimetableManager{
    public String[] classNames = new String[]{"M", "FM", "P", "CS", "C", "B"};

    static Random rn = new Random();
    public static int[] classN = new int[]{4,4,3,2,2,1};
    public static int[] lessonN = new int[]{2,2,3,3,3,3};
    public static int[] simLessonsLimit = new int[]{2,2,1,1,1,1};

    public static int recursionLimit = 1000000000;
    static int recursionCounter = 0;

    public static ArrayList<Student>[][] putStudentsIntoClasses(Student[] students){
        int[] stats = new int[]{0,0,0,0,0,0};
        ArrayList<Student>[][] classStudentList = new ArrayList[classN.length][];
        for(int i = 0; i < classN.length;i++){
            classStudentList[i]=new ArrayList[classN[i]];
            for(int j = 0; j < classN[i];j++){
                classStudentList[i][j] = new ArrayList<Student>();
            }
        }
        for (Student student : students) {
            for (int j = 0; j < student.subjectsTaken.length; j++) {
                student.classIDS[j] = stats[student.subjectsTaken[j]] % classN[student.subjectsTaken[j]];
                classStudentList[student.subjectsTaken[j]][student.classIDS[j]].add(student);
                stats[student.subjectsTaken[j]]++;
            }
        }
        return classStudentList;
    }
    public static void MoveStudent(ArrayList<Student>[][] classStudentList, int subjectIndex,int sourceClassIndex, int targetClassIndex, int studentIndex){
        {
            Student student = classStudentList[subjectIndex][sourceClassIndex].get(studentIndex);
            student.classIDS[student.subjectMapping[subjectIndex]] = targetClassIndex;
            classStudentList[subjectIndex][targetClassIndex].add(classStudentList[subjectIndex][sourceClassIndex].remove(studentIndex));
        }
        if(subjectIndex == 1){
            Student student = classStudentList[0][sourceClassIndex].get(studentIndex);
            student.classIDS[student.subjectMapping[0]] = targetClassIndex;
            classStudentList[0][targetClassIndex].add(classStudentList[0][sourceClassIndex].remove(studentIndex));
        }else if(subjectIndex==0){
            Student student = classStudentList[1][sourceClassIndex].get(studentIndex);
            student.classIDS[student.subjectMapping[1]] = targetClassIndex;
            classStudentList[1][targetClassIndex].add(classStudentList[1][sourceClassIndex].remove(studentIndex));
        }
    }
    public static void MoveStudent(ArrayList<Student>[][] classStudentList,int subjectIndex,int sourceClassIndex, int targetClassIndex, int studentIndex, int targetStudentIndex){
        {
            Student student = classStudentList[subjectIndex][sourceClassIndex].get(studentIndex);
            student.classIDS[student.subjectMapping[subjectIndex]] = targetClassIndex;
            classStudentList[subjectIndex][targetClassIndex].add(targetStudentIndex, classStudentList[subjectIndex][sourceClassIndex].remove(studentIndex));
        }
        if(subjectIndex == 1){
            Student student = classStudentList[0][sourceClassIndex].get(studentIndex);
            student.classIDS[student.subjectMapping[0]] = targetClassIndex;
            classStudentList[0][targetClassIndex].add(targetStudentIndex,classStudentList[0][sourceClassIndex].remove(studentIndex));
        }else if(subjectIndex==0){
            Student student = classStudentList[1][sourceClassIndex].get(studentIndex);
            student.classIDS[student.subjectMapping[1]] = targetClassIndex;
            classStudentList[1][targetClassIndex].add(targetStudentIndex,classStudentList[1][sourceClassIndex].remove(studentIndex));
        }
    }
    public static double OptimisationFitnessFunction(Student[] students, ArrayList<Student>[][] classStudentList){
        double fitness = 0;
//        for(int i = 0; i < students.length;i++){
//            for(int j = 0; j < students.length;j++){
//                Student student1 = students[i];
//                Student student2 = students[j];
//                int countMutualClasses = 0;
//                for(int k = 0; k < student1.subjectsTaken.length;k++){
//                    for(int h = 0; h < student2.subjectsTaken.length;h++){
//                        if(student1.subjectsTaken[k] == student2.subjectsTaken[h]){
//                            if(student1.classIDS[k] == student2.classIDS[h]) countMutualClasses++;
//                        }
//                    }
//                }
//                fitness += Math.abs(student1.subjectsTaken.length/2 - countMutualClasses);
//            }
//        }
//        for(int i = 0; i < classN.length;i++){
//            int[] classSizes = new int[classN[i]];
//            double mean = 0;
//            double variance = 0;
//            for(int j = 0; j < classN[i];j++){
//                classSizes[j] = classStudentList[i][j].size();
//                mean += (double) classSizes[j] /classN[i];
//            }
//            for(int j = 0; j < classN[i];j++){
//                variance += Math.pow(mean-classSizes[j], 2)/classN[i];
//            }
//            //fitness -= Math.pow(variance,2);
//        }
        fitness += findSimLessonsRecursively(classStudentList, new ArrayList<>()).length;
        return fitness;
    }
    public static void OptimiseStudents(Student[] students, ArrayList<Student>[][] classStudentList, int generationN, int populationSize, int repetitionLimit, boolean debugOutput){
        int repetitionCounter = 0;
        double repF = -2147000000;
        for(int i = 0; i < generationN;i++){
            int[] bestMove = new int[4];
            double bestFitness = -2147000000;
            for(int j = 0; j < populationSize;j++){
                int rnSubject = Math.abs(rn.nextInt()) % classN.length;
                int rnClass = Math.abs(rn.nextInt()) % classN[rnSubject];
                if(classStudentList[rnSubject][rnClass].isEmpty())continue;
                int rnTCLass = Math.abs(rn.nextInt()) % classN[rnSubject];
                int rnStudent = Math.abs(rn.nextInt()) % classStudentList[rnSubject][rnClass].size();

                MoveStudent(classStudentList, rnSubject, rnClass, rnTCLass, rnStudent);
                double fitness= OptimisationFitnessFunction(students, classStudentList);
                if(fitness > bestFitness){
                    bestFitness = fitness;
                    bestMove = new int[]{rnSubject, rnClass, rnTCLass, rnStudent};
                }

                MoveStudent(classStudentList, rnSubject, rnTCLass, rnClass, classStudentList[rnSubject][rnTCLass].size()-1, rnStudent);

            }
            MoveStudent(classStudentList, bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
            if(bestFitness != repF) {repetitionCounter = 0;repF = bestFitness;}
            repetitionCounter++;
            if(debugOutput){
                System.out.println("[DEBUG] [Optimisation] " + "Generation No " + (i+1) + " finished. Fitness: " + bestFitness + " Repetition counter: " + repetitionCounter);
            }
            if(repetitionCounter >= repetitionLimit){
                if(debugOutput){
                    System.out.println("[DEBUG] [Optimisation] " + "Reached the repetition limit. Quitting...");
                }
                break;
            }
        }
    }

    public static int[][][] findSimLessonsRecursively(ArrayList<Student>[][] classStudentList, ArrayList<int[]> currentLessons){
        int[][] crntLessons = new int[currentLessons.size()][];
        ArrayList<int[][]> returnedList = new ArrayList<>();
        ArrayList<int[]> currentLessonsCopy = new ArrayList<>();
        for(int i = 0; i < crntLessons.length;i++){
            crntLessons[i] = currentLessons.get(i);
            currentLessonsCopy.add(crntLessons[i]);
        }
        returnedList.add(crntLessons);
        int[][] simLessons = findSimLessonsFromExisting(classStudentList, crntLessons);
        if(simLessons.length == 0){
            return null;
        }

        for (int[] simLesson : simLessons) {
            currentLessonsCopy.add(simLesson);
            int[][][] moreLessons = findSimLessonsRecursively(classStudentList, currentLessonsCopy);
            if (moreLessons != null) {
                returnedList.addAll(Arrays.asList(moreLessons));
            }
            currentLessonsCopy.remove(simLesson);
        }

        ArrayList<int[][]> to_delete = new ArrayList<>();
        for (int[][] value : returnedList) {
            if (value.length == 0) to_delete.add(value);
        }
        for (int[][] ints : to_delete) {
            returnedList.remove(ints);
        }
        int[][][] returnArray = new int[returnedList.size()][][];
        for(int i = 0; i < returnArray.length;i++){
            returnArray[i] = returnedList.get(i);
        }
        Arrays.sort(returnArray, new Comparator<int[][]>() {
            @Override
            public int compare(int[][] o1, int[][] o2) {
                return Integer.compare(o2.length, o1.length);
            }
        });
        return returnArray;
    }


    public static int[][] findSimLessonsFromExisting(ArrayList<Student>[][] classStudentList, int[][] currentLessons){
        ArrayList<int[]> validLessonsList = new ArrayList<>();
        for(int i = 0; i < classStudentList.length;i++){
            for(int j = 0; j < classStudentList[i].length;j++){
                ArrayList<Student> studentClass = classStudentList[i][j];
                boolean valid = true;
                for (Student searchStudent : studentClass) {
                    for (int[] currentLesson : currentLessons) {
                        ArrayList<Student> searchedClass = classStudentList[currentLesson[0]][currentLesson[1]];
                        if (searchedClass.contains(searchStudent)) {
                            valid = false;
                            break;
                        }
                    }
                }

                if(valid){
                    validLessonsList.add(new int[]{i, j});
                }
            }
        }
        int[][] validLessons = new int[validLessonsList.size()][];
        for(int i= 0; i < validLessons.length;i++){
            validLessons[i] = validLessonsList.get(i);
        }
        return validLessons;
    }

    public static int[][][] findTimetableSolutions(int[][][] simLessons, boolean outputDebug){
        int[][] stats = new int[classN.length][];
        for(int i = 0; i < stats.length;i++){
            stats[i] = new int[classN[i]];
            Arrays.fill(stats[i], lessonN[i]);
        }
        ArrayList<int[][]> converted = new ArrayList<>(Arrays.asList(simLessons));
        recursionCounter = 0;
        return FTSR(converted, stats, new ArrayList<>(),outputDebug);
    }

    static int[][][] FTSR(ArrayList<int[][]> simLessonsLeft, int[][] stats, ArrayList<int[][]> currentSimLessons, boolean outputDebug){
        ArrayList<int[][]> newValid = new ArrayList<>();
        for (int[][] simLesson : simLessonsLeft) {
            boolean valid = true;
            for (int[] ints : simLesson) {
                if (stats[ints[0]][ints[1]] == 0) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                newValid.add(simLesson);
            }
        }
        if(newValid.isEmpty()){
            if(outputDebug){
                if(recursionCounter % (recursionLimit/100) == 0) {
                    System.out.println("[DEBUG] " + "[Recursive solution search] " + "Progress: " + (recursionCounter*100.0/recursionLimit) + "% ");
                }
            }
            boolean isSuccess = true;
            root:
            for (int[] stat : stats) {
                for (int i : stat) {
                    if (i > 0) {
                        isSuccess = false;
                        break root;
                    }
                }
            }
            if(isSuccess){
                int[][][] solution = new int[currentSimLessons.size()][][];
                for(int i = 0; i < solution.length;i++){
                    solution[i] = currentSimLessons.get(i);
                }
                return solution;
            }else{
                recursionCounter++;
                return null;
            }
        }
        for(int i = 0; i < newValid.size();i++){
            int[][] validSimLesson = newValid.get(i);
            for (int[] ints : validSimLesson) {
                stats[ints[0]][ints[1]]--;
            }
            newValid.remove(validSimLesson);
            currentSimLessons.add(validSimLesson);
            int[][][] solution = FTSR(newValid, stats, currentSimLessons, outputDebug);
            if(solution != null) return solution;
            if(recursionCounter >= recursionLimit) return null;
            newValid.add(validSimLesson);
            currentSimLessons.remove(validSimLesson);
            for (int[] ints : validSimLesson) {
                stats[ints[0]][ints[1]]++;
            }
        }
        return null;
    }
}
