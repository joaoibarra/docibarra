package br.com.ibarra.docibarra

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import br.com.ibarra.docibarra.extensions.loadResponse
import br.com.ibarra.docibarra.ui.doctor.DoctorListActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

fun arrange(rule: ActivityTestRule<DoctorListActivity>, func: DoctorListActivityArrangeRobot.() -> Unit) = DoctorListActivityArrangeRobot(rule).apply { func() }
fun act(func: DoctorListActivityActRobot.() -> Unit) = DoctorListActivityActRobot().apply { func() }
fun assert(func: DoctorListActivityAssertRobot.() -> Unit) = DoctorListActivityAssertRobot().apply { func() }

class DoctorListActivityArrangeRobot(private val rule: ActivityTestRule<DoctorListActivity>) {

    private val coverResponse by lazy { "doctor_list.json".loadResponse() }

    fun launchActivity() {
        rule.launchActivity(Intent())
    }

    fun mockWebServer(mockWebServer: MockWebServer) {
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when {
                    request.path.orEmpty().contains("doctors") -> {
                        MockResponse().setResponseCode(200).setBody(coverResponse)
                    }
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }

        mockWebServer.dispatcher = dispatcher
    }
}

class DoctorListActivityActRobot {
}

class DoctorListActivityAssertRobot {
}