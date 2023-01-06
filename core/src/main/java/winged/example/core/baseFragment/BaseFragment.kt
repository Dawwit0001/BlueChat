package winged.example.core.baseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

/**
This is a base class, every "fragment" in this app extends this class
it is managing navigation and binding variables (which removes some boilerplate, repeating code)
 */

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }
    /** navigate to another fragment in the same graph,
     *  use exposed interfaces to navigate outside of the current graph!!!

     *  side note: To be honest adding try and catch block here would be useful
     * (would probably do that if I was not coding this on my own) */
    fun navigateTo(
        targetDestination: Int,
        dataToPass: Bundle? = null,
        popUpToInclusive: Boolean = false
    ) {
        findNavController().navigate(
            resId = targetDestination,
            args = dataToPass,
            navOptions = NavOptions.Builder().setPopUpTo(
                destinationId = targetDestination, inclusive = popUpToInclusive
            ).build()
        )
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
