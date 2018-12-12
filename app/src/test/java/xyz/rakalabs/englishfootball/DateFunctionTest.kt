package xyz.rakalabs.englishfootball

import org.junit.Assert
import org.junit.Test
import xyz.rakalabs.englishfootball.utils.UtilDate

class DateFunctionTest {
    private val dateScheduler = "2018-12-15"
    private val timeSchedule = "00:00:18+00:00"
    @Test
    fun checkIsNiceDate() {
        Assert.assertEquals("Sat 15,Dec 2018", UtilDate.getSheduleDate(dateScheduler, timeSchedule, true))
    }
    @Test
    fun checkIsNiceTime() {
        Assert.assertEquals("07:00:18", UtilDate.getSheduleDate(dateScheduler, timeSchedule, false))
    }
}