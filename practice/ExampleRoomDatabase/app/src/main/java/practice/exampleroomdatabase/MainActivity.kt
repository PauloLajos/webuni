package practice.exampleroomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import practice.exampleroomdatabase.data.ExampleDao
import practice.exampleroomdatabase.data.ExampleDatabase
import practice.exampleroomdatabase.data.ExampleEntity
import practice.exampleroomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var data: ExampleDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        data = ExampleDatabase.getInstance(this).exampleDao()
        var result: List<ExampleEntity>? = null
        CoroutineScope(Dispatchers.IO).async {
            if (data.getAllExamples().isEmpty()) {
                data.insertGrades(ExampleEntity(null, "123Buyme", "Sick?yes", "1 Most Popular"))
                data.insertGrades(ExampleEntity(null, "123Buyme", "Sick?yes", "2 Most Popular"))
                data.insertGrades(ExampleEntity(null, "123Buyme", "Sick?yes", "3 Most Popular"))
                data.insertGrades(ExampleEntity(null, "123Buyme", "Sick?yes", "4 Most Popular"))
            }
            result = data.getAllExamples()
        }

        setContentView(view)

        binding.tvData.setOnClickListener {
            binding.tvData.text = ""
            result?.forEach {
                binding.tvData.append("${it.token}, ${it.designer}, ${it.desc}\n")
            }
        }
    }
}