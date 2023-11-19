package com.example.sgpacgpacalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.util.Log
import kotlin.math.log

class MainActivity : AppCompatActivity() {


    lateinit var selected_edit_text : EditText;

    lateinit var cgpa_head : TextView;
    lateinit var cgpa_value : TextView;

    lateinit var perc_head : TextView;
    lateinit var perc_value : TextView;

    var university_list_ar: Array<String> = Array(5) { "" };
    var conv_from_list: Array<String> = Array(3) { "" };

    var current_univ = 0;
    var current_conv_from = 0;




    fun toastMsg(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    // textWatcher is for watching any changes in editText
    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            recalc_values();
        }

        override fun afterTextChanged(s: Editable) {
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected_edit_text = findViewById(R.id.edit_text)
        university_list_ar = resources.getStringArray(R.array.univ_list)
        conv_from_list = resources.getStringArray(R.array.edit_txt_vals_list)


        cgpa_head = findViewById(R.id.cgpa_head);
        cgpa_value = findViewById(R.id.cgpa_value);

        perc_head = findViewById(R.id.perc_head);
        perc_value = findViewById(R.id.perc_value);


        selected_edit_text.addTextChangedListener(textWatcher)
        getUniversity();
        getConvFrom();
    }

    fun update_univ(){
        // capture university change
        val spinner_val = findViewById<Spinner>(R.id.spinner).selectedItem
        current_univ = match_str(university_list_ar,spinner_val.toString());

        //print_data(current_univ.toString());
    }

    fun update_conv_from(){
        // capture university change
        val conv_from = findViewById<Spinner>(R.id.edit_value_select).selectedItem

        current_conv_from = match_str(conv_from_list,conv_from.toString());


        when (current_conv_from) {
            1 -> {
                // CGPA
                cgpa_head.setText(conv_from_list[0])
                perc_head.setText(conv_from_list[2])
            }
            2 -> {
                // Percent
                cgpa_head.setText(conv_from_list[0])
                perc_head.setText(conv_from_list[1])
            }
            else -> {
                // SGPA
                cgpa_head.setText(conv_from_list[1])
                perc_head.setText(conv_from_list[2])

            }
        }
        //print_data(current_conv_from.toString());
    }

    fun recalc_values(){

        var result_1 = 0.0;
        var result_2 = 0.0;
        var capture = selected_edit_text.text.toString().toDouble();

        

        when (current_conv_from) {
            1 -> {
                // 2 : CGPA >> SGPA, percent

            }

            2 -> {
                // 1 : percent >> SGPA, CGPA

            }

            else -> {
                // 0 : SGPA >> CGPA, percent

            }
        }


        when (current_univ) {
            1 -> {
                // (VTU) Visvesvaraya Technological University
            }
            2 -> {
                // University of Mumbai
            }
            3 -> {
                // Maulana Abul Kalam Azad University
            }
            4 -> {
                // University of Calicut

            }
            else -> {
                // sppu
                when (current_conv_from) {
                    1 -> {
                        // 2 : CGPA >> SGPA, percent

                    }
                    2 -> {
                        // 1 : percent >> SGPA, CGPA
                        result_1 = capture / 8.8;
                        //result_2 = 0.0;
                    }
                    else -> {
                        // 0 : SGPA >> CGPA, percent
                        //result_1 = 0.0;
                        result_2 = capture * 8.8;
                    }
                }

            }



        }

        cgpa_value.setText(result_1.toString());
        perc_value.setText(result_1.toString());

    }

    fun match_str(test_list: Array<String>,matcher: String): Int {

        var tmp = -1;
        for (x in test_list.indices) {
            if (test_list[x] == matcher) {
                tmp = x;
            }
        }
        return tmp;
    }


    fun print_data(msg: String){
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show()
    }

    fun getUniversity(){

        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, university_list_ar)
            //spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    /*
                    Toast.makeText(this@MainActivity,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()
                    */
                    update_univ()
                    // (this@MainActivity,getString(R.string.selected_item) + " " + "" + languages[position], Toast.LENGTH_SHORT)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    fun getConvFrom(){

        val conv_from_snipper = findViewById<Spinner>(R.id.edit_value_select)
        if (conv_from_snipper != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, conv_from_list)

            conv_from_snipper.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    update_conv_from();
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }
}
