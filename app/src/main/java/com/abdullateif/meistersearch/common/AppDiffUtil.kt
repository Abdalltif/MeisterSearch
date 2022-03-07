import androidx.recyclerview.widget.DiffUtil
import com.abdullateif.meistersearch.data.remote.dto.TaskDto
import com.abdullateif.meistersearch.domain.entity.Task
import com.abdullateif.meistersearch.domain.entity.TaskDetails

class AppDiffUtil(
    private val oldList: List<TaskDetails>,
    private val newList: List<TaskDetails>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].taskId == newList[newItemPosition].taskId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].taskId != newList[newItemPosition].taskId -> {
                false
            }
            oldList[oldItemPosition].taskId != newList[newItemPosition].taskId -> {
                false
            }
            else -> true
        }
    }
}