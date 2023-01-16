package com.example.draganddropmarcs

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import androidx.core.view.children
import com.example.draganddropmarcs.databinding.ActivityGameBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast

class ActivityGame : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private var checkingFirstCard = false
    private var tempCheckingID = -1

    private var cardsList : ArrayList<Card> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.firstObj.setOnLongClickListener{
            val shadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(null, shadow, Pair(it, 1), 0)
        }

        binding.secondObj.setOnLongClickListener{
            val shadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(null, shadow, Pair(it, 1), 0)
        }

        binding.thirdObj.setOnLongClickListener{
            val shadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(null, shadow, it, 0)
        }

        binding.fourthObj.setOnLongClickListener{
            val shadow = View.DragShadowBuilder(it)
            it.startDragAndDrop(null, shadow, it, 0)
        }

        binding.noCoupleChipGroup.setOnDragListener(noCoupleDragListener)
        binding.coupleChipGroup.setOnDragListener(coupleDragListener)

        // Init cards
        cardsList.add(Card(1, binding.firstObj))
        cardsList.add(Card(2, binding.secondObj))
        cardsList.add(Card(1, binding.thirdObj))
        cardsList.add(Card(2, binding.fourthObj))

    }

    private val noCoupleDragListener = View.OnDragListener { destinationView, draggingData ->
        val event = draggingData.action
        val movableObj = draggingData.localState // 3r parametre

        when(event){
            DragEvent.ACTION_DRAG_STARTED -> destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENTERED -> destinationView.setBackgroundColor(Color.YELLOW)
            DragEvent.ACTION_DRAG_EXITED -> destinationView.setBackgroundColor(Color.MAGENTA)
            DragEvent.ACTION_DRAG_ENDED -> destinationView.setBackgroundColor(Color.DKGRAY)
            DragEvent.ACTION_DROP -> {
                if(movableObj !is Chip)
                    return@OnDragListener true



                ChipGroup::class.safeCast(movableObj.parent)?.removeView(movableObj)
                ChipGroup::class.safeCast(destinationView)?.addView(movableObj)
            }
        }
        true
    }

    private val coupleDragListener = View.OnDragListener { destinationView, draggingData ->
        val event = draggingData.action
        val movableObj = draggingData.localState // 3r parametre

        when(event){
            DragEvent.ACTION_DRAG_STARTED -> destinationView.setBackgroundColor(Color.GREEN)
            DragEvent.ACTION_DRAG_ENTERED -> destinationView.setBackgroundColor(Color.YELLOW)
            DragEvent.ACTION_DRAG_EXITED -> destinationView.setBackgroundColor(Color.GREEN)
            DragEvent.ACTION_DRAG_ENDED -> destinationView.setBackgroundColor(Color.DKGRAY)
            DragEvent.ACTION_DROP -> {



                /*if(checkingFirstCard){
                    for (i in 0..cardsList.size - 1){
                        if()
                    }
                }
                else{

                }
*/
                if(movableObj !is Pair<*, *>)
                    return@OnDragListener true

                val chip = movableObj.first as? Chip ?: return@OnDragListener true
                val id = movableObj.second as? Int ?: return@OnDragListener true

                ChipGroup::class.safeCast(movableObj.parent)?.removeView(movableObj)
                ChipGroup::class.safeCast(destinationView)?.addView(movableObj)

                /*var image: Drawable? = null
                ChipGroup::class.safeCast(destinationView)?.children?.forEach {
                    val image2 = Chip::class.safeCast(it)?.chipIcon
                    if (image == null)
                        image = image2
                    else {
                        if (image == image2)
                        {

                        }
                        else {
                            ChipGroup::class.safeCast(destinationView)?.children?.forEach {
                                ChipGroup::class.safeCast(movableObj.parent)?.removeView(it)
                                ChipGroup::class.safeCast(binding.noCoupleChipGroup)?.addView(it)
                            }
                        }
                    }
                }*/
            }
        }
        true
    }
}