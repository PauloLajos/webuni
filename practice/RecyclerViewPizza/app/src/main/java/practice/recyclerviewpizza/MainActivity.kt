package practice.recyclerviewpizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import practice.recyclerviewpizza.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var foodList : ArrayList<Food>
    private lateinit var foodAdapter : FoodAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        foodList = ArrayList()
        foodAdapter = FoodAdapter(foodList)
        foodListItems()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = foodAdapter


    }

    private fun foodListItems(){
        foodList.add(Food(R.drawable.pizza1,"Chicken Pizza"))
        foodList.add(Food(R.drawable.pizza2,"Beef Chicken"))
        foodList.add(Food(R.drawable.noodles1,"Normal Pizza"))
        foodList.add(Food(R.drawable.noodles2,"Sweet Chicken"))
        foodList.add(Food(R.drawable.paneer,"Mini Pizza"))
        foodList.add(Food(R.drawable.pasta,"Extra Chicken"))
        foodList.add(Food(R.drawable.piza_spagethi,"Meat Pizza"))
        foodList.add(Food(R.drawable.pizza12,"Vegez Chicken"))
        foodList.add(Food(R.drawable.pizza1,"Chicken Pizza"))
        foodList.add(Food(R.drawable.pizza2,"Beef Chicken"))
        foodList.add(Food(R.drawable.noodles1,"Normal Pizza"))
        foodList.add(Food(R.drawable.noodles2,"Sweet Chicken"))
        foodList.add(Food(R.drawable.paneer,"Mini Pizza"))
        foodList.add(Food(R.drawable.pasta,"Extra Chicken"))
        foodList.add(Food(R.drawable.piza_spagethi,"Meat Pizza"))
        foodList.add(Food(R.drawable.pizza12,"Vegez Chicken"))
    }
}