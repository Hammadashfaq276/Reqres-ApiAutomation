package Utility;

import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("API Automation Report");
            spark.config().setReportName("API Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add tester info
            extent.setSystemInfo("Tester", "Hammad Ashfaq"); // <-- Replace "Your Name" with actual tester name
            extent.setSystemInfo("Environment", "QA");   // Optional
            extent.setSystemInfo("Project", "API Automation"); // Optional
        }
        return extent;

    }

}
