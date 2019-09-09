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
//infix fun DoctorListActivityArrangeRobot.act(func: DoctorListActivityActRobot.() -> Unit): DoctorListActivityActRobot {
//    return also(func)
//}

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

//    fun clickHighlight() {
//        Espresso.onView(ViewMatchers.withId(R.id.fl_highlight_background)).perform(click())
//    }
//
//    fun clickMenuButton() {
//        recyclerView(R.id.rv_buttons) {
//            atPosition(0) {
//                click()
//            }
//        }
//    }
//
//    fun clickSuggestionButton() {
//        recyclerView(R.id.rv_suggestions) {
//            atPosition(0) {
//                click()
//            }
//        }
//    }
}

class DoctorListActivityAssertRobot {
//    fun highlightIsVisible() {
//        displayed {
//            allOf {
//                id(R.id.tv_highlight_title)
//                text("Tenha o melhor do HBO")
//            }
//            allOf {
//                id(R.id.tv_highlight_subtitle)
//                text("canal com os melhores filmes")
//            }
//        }
//    }
//
//    fun buttonListIsVisible() {
//        displayed {
//            allOf {
//                id(R.id.rv_buttons)
//            }
//            allOf {
//                id(R.id.tv_buttons_title)
//                text("Aproveite o melhor conteúdo")
//            }
//        }
//    }
//
//    fun suggestionListIsVisible() {
//        displayed {
//            allOf {
//                id(R.id.rv_suggestions)
//            }
//            allOf {
//                id(R.id.tv_suggestions_title)
//                text("O que está bombando")
//            }
//        }
//    }
//
//    fun redirectToLogin() {
//        sentIntent {
//            className(LoginSheetActivity::class.java.name)
//        }
//    }
}