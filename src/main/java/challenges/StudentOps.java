package challenges;

import com.google.common.util.concurrent.AtomicDouble;
import data.FetchData;
import domain.Student;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StudentOps {
    //  1. Filter Students by Gender: Write a method to filter a list of students by their gender.

    public static List<Student> filterStudentsByGender(List<Student> students, String gender) {
        return students.stream()
                .filter(student -> student.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
    }

    //2. Sort Students by Age: Sort the list of students by their age (based on date of birth).
    public static List<Student> sortStudentsByAge(List<Student> students) {
        LocalDate currentDate = LocalDate.now();
        students.forEach(student -> {
            LocalDate dob = student.getDob();
            Period period = Period.between(dob, currentDate);
            student.setAge(period.getYears());
        });
        return  students.stream()
                    .sorted(Comparator.comparing(Student::getAge))
                    .collect(Collectors.toList());
        }

    public static int getAverageAgeOfStudents(List<Student> students){

        OptionalDouble average = (students.stream().flatMapToInt(s ->
             IntStream.of(s.getAge())
        ).average());
        int result = (int)average.getAsDouble();
        return result;
    }

    public static void printAllStudentNames(List<Student> students){
        students.stream().forEach((student) ->{
            System.out.printf("%s  %s \n",student.getFirst_name(),student.getLast_name());
        });
    }

    public static Map<String,List<Student>> groupStudentsByGender(List<Student> students){
        return students.stream().collect(Collectors.groupingBy(student -> student.getGender()));
    }

    public static int getMaxAge(List<Student> students){
        return students.stream().max((s1, s2) ->{
            return s1.getAge() - s2.getAge();
        }).get().getAge();
    }

    public static Map<Integer,Student> transformListToMap(List<Student> students){
         return students.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
    }

    public static List<String> getStudentsEmails(List<Student> students){
        return students.stream().map((s) ->{
            return s.getEmail();
        }).collect(Collectors.toList());
    }

    public static boolean checkIfStudentISAdult(Student student){
        return student.getAge() >= 18;
    }

    public static Map<String, Long> getStudentsCountByGender(List<Student> students){
        return students.stream().collect(Collectors.groupingBy(Student::getGender,Collectors.counting()));
    }

    public static Optional<Student> getYoungestFemaieStudent(List<Student> students){
        Optional<Student> getYoungestFemaleStudent = Optional.of(students.stream().filter((s) -> {
            return s.getGender().equalsIgnoreCase("Female");
        }).min((s1, s2) -> {
            return s1.getAge() - s2.getAge();
        }).get());
        return getYoungestFemaleStudent;
    }

    public static String getAllStudentNames(List<Student> students){
        return students.stream().flatMap(s -> Stream.of(s.getFirst_name() + " " + s.getLast_name())).collect(Collectors.joining(", "));
    }

    public static int getSumOfallStudentAges(List<Student> students)
    {
        return students.stream().flatMapToInt((student) ->{
            return IntStream.of(student.getAge());
        }).sum();
    }

    public static Boolean checkIfAllStudentsAreAdults(List<Student> students){
        AtomicBoolean result = new AtomicBoolean(true);
        students.stream().forEach((student) ->{
            if(student.getAge() < 18){
                result.set(false);
            }
        });
        return result.get();
    }

    public static Student getOldestStudent(List<Student> students){
        return students.stream().max((s1,s2) ->{
            return s1.getAge() - s2.getAge();
        }).get();
    }

    public static List<Student> convertAllStudentFirstNamesToUpperCase(List<Student> students){
        return students.stream().map((student) ->{
            return new Student(student.getId(),student.getFirst_name().toUpperCase(),student.getLast_name(),student.getEmail(),student.getGender(),student.getDob().toString());
        }).collect(Collectors.toList());
    }

    public static Student getStudentById(List<Student> students,int id){
        return students.stream().filter(s ->{
            return s.getId() == id;
        }).findFirst().orElse(null);
    }

    public static Map<Integer,List<Student>> getStudentsByAge(List<Student> students){
        return students.stream().collect(Collectors.groupingBy(Student::getAge));
    }

    public static double getStandardDeviationOfStudentsAge(List<Student> students){
        int age_sum = students.stream().mapToInt((s) ->{
            return s.getAge();
        }).sum();
        double age_avg = students.stream().mapToInt((s) ->{
            return s.getAge();
        }).average().getAsDouble();
        AtomicDouble standard_deviation = new AtomicDouble(0.0);
        students.stream().forEach((s) ->{
            standard_deviation.addAndGet( Math.pow(s.getAge() - age_avg,2));
        });
        return Math.sqrt(standard_deviation.get()/students.size());
    }
}
