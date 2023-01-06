package winged.example.chat_feature.list_of_previous_conversations

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import winged.example.chat_feature.databinding.FragmentListOfPreviousConversationsBinding
import winged.example.chat_feature.navigation.ChatActions
import winged.example.core.baseFragment.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class ListOfPreviousConversations : BaseFragment<FragmentListOfPreviousConversationsBinding>(
    FragmentListOfPreviousConversationsBinding::inflate
) {

    @Inject
    lateinit var actions: ChatActions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpScanScreenNavigationButton()
    }

    private fun setUpScanScreenNavigationButton() {
        binding.addFriendIB.setOnClickListener {
            actions.navigateToScanScreen()
        }
    }
}
