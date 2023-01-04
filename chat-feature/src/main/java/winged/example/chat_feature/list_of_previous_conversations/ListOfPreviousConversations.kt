package winged.example.chat_feature.list_of_previous_conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import winged.example.chat_feature.databinding.FragmentListOfPreviousConversationsBinding
import winged.example.chat_feature.navigation.ChatActions
import winged.example.core.baseFragment.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class ListOfPreviousConversations : BaseFragment() {

    @Inject
    lateinit var actions: ChatActions

    private var _binding: FragmentListOfPreviousConversationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfPreviousConversationsBinding.inflate(inflater, container, false)
        setUpScanScreenNavigationButton()
        return binding.root
    }

    private fun setUpScanScreenNavigationButton() {
        binding.addFriendIB.setOnClickListener {
            actions.navigateToScanScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
