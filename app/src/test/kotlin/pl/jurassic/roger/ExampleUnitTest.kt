package pl.jurassic.roger

import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        Random().nextInt(3)

        val tmp = MutableList(49) { DateTime.now().plusDays(it + 1).dayOfYear }

        println(DateTime.now().dayOfWeek)

        for(x in tmp.first()..tmp.last()) {
            if(!tmp.contains(x)) {
                tmp.add(x)
            }
        }


        val x = tmp.chunked(7)

        print(x)

        assertEquals(4, 2 + 2)
    }
}
