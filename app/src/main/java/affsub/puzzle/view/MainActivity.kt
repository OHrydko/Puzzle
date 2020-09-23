package affsub.puzzle.view

import affsub.puzzle.R
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        onListFragmentClick()
    }

    fun onPuzzleFragmentClick() {
        navController.navigate(R.id.puzzleFragment)
    }

    fun onListFragmentClick() {
        navController.navigate(R.id.listFragment)

    }

}