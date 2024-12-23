import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantapp.models.Mission
import com.google.firebase.database.*

class MissionViewModel : ViewModel() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("missions")

    private val _mission = MutableLiveData<Mission?>()
    val mission: MutableLiveData<Mission?> get() = _mission

    fun fetchMission(missionId: String) {
        database.child(missionId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mission = snapshot.getValue(Mission::class.java)
                if (mission != null) {
                    _mission.value = mission
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun activateMission() {
        _mission.value?.let { mission ->
            mission.isActive = true
            database.child(mission.missionName).setValue(mission)
        }
    }
}
