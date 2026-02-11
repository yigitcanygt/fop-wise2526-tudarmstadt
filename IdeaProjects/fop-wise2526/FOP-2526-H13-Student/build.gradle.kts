import org.sourcegrade.jagr.gradle.task.grader.GraderRunTask

plugins {
    alias(libs.plugins.algomate)
}

exercise {
    assignmentId.set("h13")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    // BEISPIEL:
    // studentId = "ab12cdef"
    // firstName = "sol_first"
    // lastName = "sol_last"
    studentId = "yy17beky"
    firstName = "Yigitcan"
    lastName = "Yigit"

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

jagr {
    graders {
        val graderPublic by getting {
            rubricProviderName.set("h13.H13_RubricProviderPublic")
        }
    }
}

tasks.run.configure {
    // make sure files can be accessed with same path as IntelliJ run
    workingDir = projectDir
}
