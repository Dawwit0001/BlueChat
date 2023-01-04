package winged.example.core.baseFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

/**
This is a base class, every "fragment" in this app extends this class
it is managing the navigation (which removes some boilerplate, repeating code)

in the current state it is not managing the binding variables
(like I did in other projects), because I found registering an activity (especially, if had to "fire"
on button click) extremely unpleasant that way

but If you wanted to implement it (and make this baseFragment manage binding variables for you),
you can take a look at this gist:
https://gist.github.com/jassielcastro/ce932f28c497c9cbdea1a44a5fa522cf
 */
abstract class BaseFragment : Fragment() {

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
}
