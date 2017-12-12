package kr.co.hs.googleplusapi.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.hs.googleplusapi.GooglePlusAPI
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * client id 생성 방법(google-services.json 생성 방법)
         * 1. https://console.developers.google.com/apis 접속하여 프로젝트 생성
         * 2. https://developers.google.com/mobile/add?platform=android&cntapi=signin&cnturl=https:%2F%2Fdevelopers.google.com%2Fidentity%2Fsign-in%2Fandroid%2Fsign-in%3Fconfigured%3Dtrue&cntlbl=Continue%20Adding%20Sign-In
         *    위에 접속하여 1번에서 생성한 프로젝트를 선택하고 해당 패키지명 입력
         * 3. 앱 서명의 sha1 해시를 요구한다. 다음 명령어를 입력하여 해시값을 얻자
         *    keytool -list -v -keystore debug.keystore -alias androiddebugkey
         *    keytool -list -v -keystore "서명파일" -alias androiddebugkey
         * 4. 마지막으로 Generate configureation file을 클릭하여 google-services.json을 내려받아 프로젝트에 추가한다.
         */
        var loginIntent = GooglePlusAPI.loginIntent(this)
        startActivityForResult(loginIntent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            10 -> {

                thread {
                    /**
                     * Person API를 사용하려면 Google API 콘솔에서 "Google People API" 사용 설정을 해야한다.
                     * 아래의 링크로 들어가서 위에서 설정한 프로젝트로 들어가서 사용설정을 해야함.
                     * https://console.developers.google.com/apis/api/people.googleapis.com/overview?project=valued-range-188806
                     */
                    var person = GooglePlusAPI.getPerson(this@MainActivity, data, "aa")
                    Log.d("a", "a")
                }
            }
        }
    }
}
