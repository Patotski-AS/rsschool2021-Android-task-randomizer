package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var maxValue: TextView? = null
    private var minValue: TextView? = null
    private var listener: OnFirstFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnFirstFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        maxValue = view.findViewById(R.id.max_value)
        minValue = view.findViewById(R.id.min_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            try {
                val min = minValue?.text.toString().toInt()
                val max = maxValue?.text.toString().toInt()
                if (min >= max || max == 0) {
                    //если введены некорректные данные отобразится предупреждение
                    Toast.makeText(context, "Invalid data entered", Toast.LENGTH_LONG).show()
                } else {
                    listener?.onFirstFragmentListener(min, max)
                }
            } catch (e: NumberFormatException) {
                //если данные не введены отобразится предупреждение
                Toast.makeText(context, "No data entered", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * @JvmStatic к функции. Если использовать эту аннотацию, компилятор создаст как
         * статический метод во включающем классе объекта, так и метод экземпляра в самом объекте.
         * т.е. без этой аннотации мы не сможем вызвать метод newInstance из Java-кода MainActivity
         */
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        interface OnFirstFragmentListener {
            fun onFirstFragmentListener(min: Int, max: Int)
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}