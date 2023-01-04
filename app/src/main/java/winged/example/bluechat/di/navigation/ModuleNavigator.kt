package winged.example.bluechat.di.navigation

import androidx.navigation.NavController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import winged.example.bluechat.NavigationRootDirections
import winged.example.chat_feature.navigation.ChatActions
import winged.example.scan_feature.navigation.ScanActions

@ActivityScoped
class ModuleNavigator @Inject constructor(
    private val navController: NavController
) : ScanActions, ChatActions {
    override fun navigateToConversationList() {
        navController.navigate(NavigationRootDirections.actionToConversations())
    }

    override fun navigateToScanScreen() {
        navController.navigate(NavigationRootDirections.actionToScan())
    }

    @Module
    @InstallIn(ActivityComponent::class)
    abstract class ActionsHandler {
        @Binds
        abstract fun scan(moduleNavigator: ModuleNavigator): ScanActions

        @Binds
        abstract fun chat(moduleNavigator: ModuleNavigator): ChatActions
    }
}
