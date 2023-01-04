package winged.example.bluechat.di.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import winged.example.bluechat.R

@Module
@InstallIn(ActivityComponent::class)
object NavControllerModule {
    @Provides
    fun navController(activity: FragmentActivity): NavController {
        return NavHostFragment.findNavController(
            activity.supportFragmentManager.findFragmentById(R.id.nav_host)!!
        )
    }
}
