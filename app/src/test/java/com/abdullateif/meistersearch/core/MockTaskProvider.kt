import com.abdullateif.meistersearch.domain.entity.TaskDetails

object MockTaskProvider {
    private fun getMockTask(id: Long = 2) =
        TaskDetails(
            taskId = id,
            taskName = "Ask mom for $",
            projectName = "Project 1",
            status = 1
        )

    fun getMockTasks() =
        (1..5).map {
            getMockTask(id = it.toLong())
        }
}