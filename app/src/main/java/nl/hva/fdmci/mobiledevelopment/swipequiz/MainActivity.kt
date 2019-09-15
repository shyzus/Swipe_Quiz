package nl.hva.fdmci.mobiledevelopment.swipequiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import nl.hva.fdmci.mobiledevelopment.swipequiz.model.Question
import nl.hva.fdmci.mobiledevelopment.swipequiz.model.QuestionAdapter


class MainActivity : AppCompatActivity() {

    var questions: ArrayList<Question> = arrayListOf()
    var questionAdapter: QuestionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        enableSwipe()
    }

    fun initViews() {
        rvQuestions.adapter = questionAdapter
        rvQuestions.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        for (i in Question.QUESTION_TEXTS.indices) {
            questions.add(Question(Question.QUESTION_TEXTS[i], Question.QUESTION_ANSWERS[i]))
        }
        questionAdapter.notifyDataSetChanged()
    }

    private fun enableSwipe() {
        val simpleItemTouchCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.LEFT) {
                        val deletedModel = questions[position]
                        questionAdapter.removeItem(position)

                        val response: String

                        response = when(deletedModel.answer) {

                            true -> {
                                "Correct! the answer was: " + deletedModel.answer
                            }

                            false -> {
                                "Incorrect! the answer was: " + deletedModel.answer
                            }
                        }

                        val snackbar = Snackbar.make(
                            rootConstraint,
                            response,
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.show()

                    } else {
                        val deletedModel = questions[position]
                        questionAdapter.removeItem(position)

                        val response: String

                        response = when(deletedModel.answer) {

                            true -> {
                                "Incorrect! the answer was: " + deletedModel.answer
                            }

                            false -> {
                                "Correct! the answer was: " + deletedModel.answer
                            }
                        }

                        val snackbar = Snackbar.make(
                            rootConstraint,
                            response,
                            Snackbar.LENGTH_LONG
                        )
                        snackbar.show()
                    }
                }


            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvQuestions)
    }

}
