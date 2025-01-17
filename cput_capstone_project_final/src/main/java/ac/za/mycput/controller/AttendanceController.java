package ac.za.mycput.controller;



import ac.za.mycput.entity.Attendance;
import ac.za.mycput.entity.Course;
import ac.za.mycput.entity.Student;
import ac.za.mycput.entity.Subject;
import ac.za.mycput.repository.AttendanceRepository;
import ac.za.mycput.repository.CourseRepository;
import ac.za.mycput.repository.StudentRepository;
import ac.za.mycput.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Method to show the attendance marking form
    @GetMapping("/mark")
    public String showMarkAttendanceForm(Model model) {
        List<Student> students = studentRepository.findAll();
        List<Course> courses = courseRepository.findAll();

        model.addAttribute("students", students);
        model.addAttribute("subjects", courses);

        return "AttendanceFunctionality/attendanceForm"; // Create a Thymeleaf template for the form
    }

    // Method to process the attendance marking form
    @PostMapping("/mark")
    public String markAttendance(@RequestParam("studentId") Long studentId,
                                 @RequestParam("subjectId") Long courseId,
                                 @RequestParam("date") LocalDate date,
                                 @RequestParam("present") boolean present) {
        // Save the attendance record to the database
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);;
        if (student != null && course!=null) {
            Attendance attendance = new Attendance();
            attendance.setDate(date);
            attendance.setStudent(student);
            attendance.setCourse(course);
            attendance.setPresent(present);

            attendanceRepository.save(attendance);

            // Redirect to a success page or back to the attendance marking form
            return "redirect:/attendance/mark";
        } else {
            // Handle errors, e.g., student or class not found
            return "error_page"; // Create an error page in your templates
        }
    }

    // Method to generate attendance reports
    @GetMapping("/report")
    public String generateAttendanceReport(Model model) {
        // Implement logic to fetch and generate attendance reports
        List<Attendance> attendanceRecords = attendanceRepository.findAll();

        model.addAttribute("attendanceRecords", attendanceRecords);

        return "attendance_report"; // Create a Thymeleaf template for the report
    }
}

