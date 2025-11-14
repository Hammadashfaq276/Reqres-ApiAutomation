package Base;
import Utility.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BaseTest {

    public static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @BeforeSuite
    public void setupExtent() {
        extent = ExtentManager.getInstance();
        System.out.println("===== Extent Report Initialized =====");
    }


    @BeforeMethod
    public void startTest(java.lang.reflect.Method method) {
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }


    @AfterMethod
    public void captureResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.get().log(Status.FAIL, "❌ Test Failed");
            test.get().log(Status.FAIL, result.getThrowable());

            // Create a dummy "screenshot" file (API response log)
            try {
                String filePath = System.getProperty("user.dir") + "/test-output/" + result.getName() + "_error.txt";
                FileWriter writer = new FileWriter(new File(filePath));
                writer.write("ERROR: " + result.getThrowable().getMessage());
                writer.close();
                test.get().addScreenCaptureFromPath(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.get().log(Status.PASS, "✔ Test Passed");
        }
    }


    @AfterSuite
    public void tearDown() {
        extent.flush();
        System.out.println("===== Extent Report Generated =====");
    }

}
