import java.util.ArrayList;
import java.util.Random;

public class main {
    public static void main(String[] args) {
        Student[] students = new Student[45];
        for(int i = 0; i < students.length;i++){
            students[i] = Student.randomStudent();
        }
        TimetableManager.putStudentsIntoClasses(students);
        for(int i = 0; i < students.length;i++){
            System.out.println(students[i].name);
            for(int j = 0; j < students[i].subjectsTaken.length;j++){
                System.out.println(students[i].subjectsTaken[j] + " " + students[i].classIDS[j]);
            }
        }
    }
}

class Student{
    public String name;
    public int[] subjectsTaken;
    public int[] classIDS;
    static Random rn = new Random();

    public Student(String name, int[] subjectsTaken){
        this.name = name;
        this.subjectsTaken = subjectsTaken;
        classIDS = new int[subjectsTaken.length];
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
    public static int[] classN = new int[]{4,4,2,2,2,1};
    public static int[] lessonN = new int[]{2,2,3,3,3,3};

    public static void putStudentsIntoClasses(Student[] students){
        int[] stats = new int[]{0,0,0,0,0,0};
        for(int i = 0; i < students.length;i++){
            for(int j = 0; j < students[i].subjectsTaken.length;j++){
                students[i].classIDS[j] = stats[students[i].subjectsTaken[j]]%classN[students[i].subjectsTaken[j]];
                stats[students[i].subjectsTaken[j]]++;
            }
        }
    }
}
