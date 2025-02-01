package com.example.bonustask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bonustask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counterFragment = CounterFragment()
    private var secondFragment = SecondFragment()
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frag_container, counterFragment)
                .commit()
            activeFragment = counterFragment
        } else {
            counterFragment = CounterFragment()
            secondFragment =  SecondFragment()

            activeFragment = supportFragmentManager.findFragmentById(R.id.frag_container)
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.counter -> switchFragment(counterFragment)
                R.id.second_page -> switchFragment(secondFragment)
            }
            true
        }
    }

    private fun switchFragment(fragment: Fragment) {
        if (activeFragment == fragment) return

        val transaction = supportFragmentManager.beginTransaction()

        if (fragment.isAdded) {
            transaction.hide(activeFragment!!).show(fragment)
        } else {
            transaction.hide(activeFragment!!).add(R.id.frag_container, fragment)
        }

        transaction.commit()
        activeFragment = fragment
    }
}
