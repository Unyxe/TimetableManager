let n_classes = [["M_FM", 4], ["CS", 2], ["P", 2], ["C", 2], ["B", 1]]
let n_lessons = [["M_FM", 2], ["CS", 3], ["P", 3], ["C", 3], ["B", 3]];
let classes = [];
let lessons = [];
let students = [];
let subject_stats = [["P", 0], ["CS", 0], ["C", 0], ["B", 0]];
for(let i = 0; i < 45;i++){
    students.push(RandomCMSStudent());
}
//students = JSON.parse('[["M_FM_P_CS",["M","FM","P","CS"],["M1","FM1","CS1","P1"]],["M_FM_P_C",["M","FM","P","C"],["M2","FM2","P2","C1"]],["M_FM_P_C",["M","FM","P","C"],["M3","FM3","P1","C2"]],["M_FM_P",["M","FM","P"],["M4","FM4","P2"]],["M_FM_P_C",["M","FM","P","C"],["M1","FM1","P1","C1"]],["M_FM_P_CS",["M","FM","P","CS"],["M2","FM2","CS2","P2"]],["M_FM_P_CS",["M","FM","P","CS"],["M3","FM3","CS1","P1"]],["M_FM_P_C",["M","FM","P","C"],["M4","FM4","P2","C2"]],["M_FM_P",["M","FM","P"],["M1","FM1","P1"]],["M_FM_P",["M","FM","P"],["M2","FM2","P2"]],["M_FM_C",["M","FM","C"],["M3","FM3","C1"]],["M_FM_P_C",["M","FM","P","C"],["M4","FM4","P1","C2"]],["M_FM_P_CS",["M","FM","P","CS"],["M1","FM1","CS2","P2"]],["M_FM_P",["M","FM","P"],["M2","FM2","P1"]],["M_FM_P_CS",["M","FM","P","CS"],["M3","FM3","CS1","P2"]],["M_FM_C",["M","FM","C"],["M4","FM4","C1"]],["M_FM_P",["M","FM","P"],["M1","FM1","P1"]],["M_FM_CS",["M","FM","CS"],["M2","FM2","CS2"]],["M_FM_P",["M","FM","P"],["M3","FM3","P2"]],["M_FM_P",["M","FM","P"],["M4","FM4","P1"]],["M_FM_P_C",["M","FM","P","C"],["M1","FM1","P2","C2"]],["M_FM_P_C",["M","FM","P","C"],["M2","FM2","P1","C1"]],["M_FM_P_C",["M","FM","P","C"],["M3","FM3","P2","C2"]],["M_FM_P",["M","FM","P"],["M4","FM4","P1"]],["M_FM_P_C",["M","FM","P","C"],["M1","FM1","P2","C1"]],["M_FM_P_CS",["M","FM","P","CS"],["M2","FM2","CS1","P1"]],["M_FM_CS",["M","FM","CS"],["M3","FM3","CS2"]],["M_FM_CS",["M","FM","CS"],["M4","FM4","CS1"]],["M_FM_P_CS",["M","FM","P","CS"],["M1","FM1","CS2","P2"]],["M_FM_CS",["M","FM","CS"],["M2","FM2","CS1"]],["M_FM_P_C",["M","FM","P","C"],["M3","FM3","P1","C2"]],["M_FM_CS_C",["M","FM","CS","C"],["M4","FM4","CS2","C1"]],["M_FM_CS",["M","FM","CS"],["M1","FM1","CS1"]],["M_FM_P_CS",["M","FM","P","CS"],["M2","FM2","CS2","P2"]],["M_FM_P_C",["M","FM","P","C"],["M3","FM3","P1","C2"]],["M_FM_P_C",["M","FM","P","C"],["M4","FM4","P2","C1"]],["M_FM_P_CS",["M","FM","P","CS"],["M1","FM1","CS1","P1"]],["M_FM_CS",["M","FM","CS"],["M2","FM2","CS2"]],["M_FM_P",["M","FM","P"],["M3","FM3","P2"]],["M_FM_P_C",["M","FM","P","C"],["M4","FM4","P1","C2"]],["M_FM_P_C",["M","FM","P","C"],["M1","FM1","P2","C1"]],["M_FM_P_CS",["M","FM","P","CS"],["M2","FM2","CS1","P1"]],["M_FM_P_CS",["M","FM","P","CS"],["M3","FM3","CS2","P2"]],["M_FM_P_C",["M","FM","P","C"],["M4","FM4","P1","C2"]],["M_FM_P",["M","FM","P"],["M1","FM1","P2"]]]');
console.log(students);
populateLessons();
let consLessons;
putClasses();
consLessons = recursivelyFindLessons(0, []);
consLessons.sort((a, b)=>{return a.length>b.length?-1:1});
//console.log(consLessons);
//console.log(turnConsLessonsIntoIndexes(consLessons));

let stats = [];
for(let i = 0; i < classes.length;i++){
    let n = 0;
    for(let j = 0; j < lessons.length;j++){
        if(classes[i] == lessons[j][0]) n++;
    }
    stats[i] = n;
}
//let res = findLessonsRecursively(0, [], consLessons, stats, lessons.length);
//let res = JSON.parse('[[12,9],[9,13,14],[9,14,13],[13,9,14],[13,14,9],[14,9,13],[14,13,9],[0,3],[0,5],[0,7],[1,2],[1,4],[1,6],[2,1],[2,5]]');
// for(let i = 0; i < res.length;i++){
//     let st = "";
//     for(let j = 0; j < res[i].length;j++){
//         st += classes[res[i][j]] + " ";
//     }
//     console.log(st);
// }

let limit = 10000000;
let c = 0;
let stats_l = [];
let last_c = -1;
for(let i = 0; i < lessons.length;i++){
    stats_l.push(0);
}



function randomIndex(array){
    return Math.floor(Math.random()*array.length);
}
function turnConsLessonsIntoIndexes(consLessons){
    let ind = [];
    for(let i = 0; i < consLessons.length;i++){
        let d = [];
        for(let j = 0;j < consLessons[i].length;j++){
            d.push(getClassInd(consLessons[i][j]));
        }
        ind.push(d);
    }
    return ind;
}
function findLessonsRecursively(d, currentLessons, consLessLeft, currentStats, currentLessonsLeft){
    if(d==0){
        //shuffleArray(consLessons)
        c = 0;
        let stats_l = [];
        for(let i = 0; i < lessons.length+1;i++){
            stats_l.push(0);
        }
        console.log(stats_l);
    }
    let leaf = true;
    let validL = [];
    for(let i = 0; i < consLessLeft.length;i++){
        let valid = true;
        for(let j = 0; j < consLessLeft[i].length;j++){
            if(currentStats[consLessLeft[i][j]]==0) {valid=false;break;}
        }
        if(valid) validL.push(consLessLeft[i]);
    }
    if(validL.length == 0){
        leaf = true;
        c++;
        if(c % 10000 == 0 && last_c != c) {
            last_c = c;
            console.log(Math.round(c/limit*1000)/10 + "%")
        };
        return -1;
    }
    for(let i = 0; i < validL.length;i++){
        let consLess = validL[i];
        let new_stats = [...currentStats];
        let newLessonsLeft = currentLessonsLeft-consLess.length;
        stats_l[newLessonsLeft]++;
        for(let j = 0; j < consLess.length;j++){
            new_stats[consLess[j]]--;
        }
        if(newLessonsLeft <= 0){
            return [[...currentLessons, consLess], new_stats, newLessonsLeft];
        }else if(c < limit){
            let h = findLessonsRecursively(d+1, [...currentLessons, consLess], [...validL.slice(0, i), ...validL.slice(i+1, consLessLeft.length)], new_stats, newLessonsLeft);
            leaf = false;
            if(h && h != -1){
                return h;
            }
        }
    }
}

function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

function getClassInd(_class){
    return classes.indexOf(_class);
}

function recursivelyFindLessons(d, currentClasses){
    let cl = getLeftClasses(currentClasses);
    if(cl == 0){
        return [currentClasses];
    }
    let jo = [currentClasses];
    for(let i = 0; i < cl.length;i++){
        let l = recursivelyFindLessons(d+1, [...currentClasses,cl[i]]);
        for(let j = 0; j < l.length;j++){
            jo.push(l[j]);
        }
    }
    return jo;
}

function populateLessons(){
    lessons = [];
    for(let i = 0;i < n_classes.length;i++){
        if(i==0){
            for(let k = 0; k < n_classes[i][1];k++){
                classes.push("M"+(k+1));
                classes.push("FM"+(k+1))
                for(let j = 0; j < n_lessons[i][1];j++){
                    lessons.push(["M"+(k+1), -1])
                    lessons.push(["FM"+(k+1), -1])
                }
            }
        }else{
            for(let k = 0; k < n_classes[i][1];k++){
                classes.push(n_classes[i][0]+(k+1));
                for(let j = 0; j < n_lessons[i][1];j++){
                    lessons.push([n_classes[i][0]+(k+1), -1])
                }
            }
        }
    }
}
function getStudentsInClass(_class){
    let s = [];
    for(let i = 0; i < students.length;i++){
        if(students[i][2].includes(_class)){
            s.push(students[i]);
        }
    }
    return s;
}
function getLeftClasses(currentClasses){
    let current_s = [];
    for(let i = 0 ; i < currentClasses.length;i++){
        let s = getStudentsInClass(currentClasses[i]);
        for(let j = 0; j < s.length;j++){
            if(!current_s.includes(s[j]))current_s.push(s[j]);
        }
    }
    let l_cl = [];
    for(let i = 0;i < classes.length;i++){
        let cl = classes[i];
        let s = getStudentsInClass(cl);
        let found = false;
        for(let j = 0;j < s.length;j++){
            if(current_s.includes(s[j])){
                found = true;
                break;
            }
        }
        if(!found){
            let f = false;
            for(let j = 0; j < currentClasses.length;j++){
                if(currentClasses[j].substring(0, currentClasses[j].length-1) == cl.substring(0, cl.length-1)){
                    f = true;
                    break;
                }
            }
            if(!f){
                l_cl.push(cl);
            }
        }
    }
    return l_cl;
}
function putClasses(){
    let P_CS = getStudentsWithSubjects(["P", "CS"], 4);
    for(let i = 0; i < P_CS.length;i++){
        P_CS[i][2].push("M1")
        P_CS[i][2].push("FM1")
        P_CS[i][2].push("P1")
        P_CS[i][2].push("CS1")
    }
    let P_C = getStudentsWithSubjects(["P", "C"], 4);
    for(let i = 0; i < P_C.length;i++){
        P_C[i][2].push("M2")
        P_C[i][2].push("FM2")
        P_C[i][2].push("P2")
        P_C[i][2].push("C1")
    }
    let C_B = getStudentsWithSubjects(["C", "B"], 4);
    for(let i = 0; i < C_B.length;i++){
        C_B[i][2].push("M3")
        C_B[i][2].push("FM3")
        C_B[i][2].push("B1")
        C_B[i][2].push("C2")
    }
    let C_CS = getStudentsWithSubjects(["C", "CS"], 4);
    for(let i = 0; i < C_CS.length;i++){
        C_CS[i][2].push("M3")
        C_CS[i][2].push("FM3")
        C_CS[i][2].push("C2")
        C_CS[i][2].push("CS2")
    }
    let C = getStudentsWithSubjects(["C"], 3);
    for(let i = 0; i < C.length;i++){
        C[i][2].push("M3")
        C[i][2].push("FM3")
        C[i][2].push("C2")
    }
    let CS = getStudentsWithSubjects(["CS"], 3);
    for(let i = 0; i < CS.length;i++){
        CS[i][2].push("M4")
        CS[i][2].push("FM4")
        CS[i][2].push("CS2")
    }
    let P = getStudentsWithSubjects(["P"], 3);
    for(let i = 0; i < P.length;i++){
        P[i][2].push("M4")
        P[i][2].push("FM4")
        P[i][2].push("P" + i < P.length/2?1:2);
    }
}
function getAllLessonStudents(){
    for(let i = 0; i < classes.length;i++){
        console.log(classes[i] + ": " + getStudentsInClass(classes[i]).length);
    }
}
function getStudentsWithSubjects(subjects, n){
    let s = [];
    for(let i = 0; i < students.length;i++){
        let meet = true;
        if(students[i][1].length != n) continue;
        for(let j = 0; j < subjects.length;j++){
            if(!students[i][1].includes(subjects[j])){
                meet = false;
                break;
            }
        }
        if(meet){
            s.push(students[i]);
        }
    }
    return s;
}

function RandomCMSStudent(){
    let do_physics = Math.random()<0.7;
    let do_chem;
    let do_comp;
    let do_biol;
    let subjects = ["M", "FM"];
    let name = "M_FM";
    if(do_physics){
        do_chem = Math.random()>0.7;
        if(!do_chem){
            do_comp = Math.random()>0.5;
        }
    }else{
        do_chem = Math.random()>0.5;
        if(do_chem){
            do_biol = Math.random()<0.2;
            if(!do_biol){
                do_comp = Math.random()>0.5;
            }
        }else{
            do_comp = Math.random()<0.8;
            if(!do_comp){
                do_biol=true;
            }
        }
    }
    if(do_physics){
        subjects.push("P");
        subject_stats[0][1]++;
    }
    if(do_comp){
        subjects.push("CS");
        subject_stats[1][1]++;
    }
    if(do_chem){
        subjects.push("C");
        subject_stats[2][1]++;
    }
    if(do_biol){
        subjects.push("B");
        subject_stats[3][1]++;
    }
    for(let i = 2; i < subjects.length;i++){
        name += "_" + subjects[i];
    }
    return [name, subjects, []];
}