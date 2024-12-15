package com.example.securednotepad

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

// Classe CustomArrayAdapter que estende ArrayAdapter e define a cor do texto do list view como branco
class CustomArrayAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)

        // Define a cor do texto como branco
        (view as TextView).setTextColor(Color.WHITE)

        return view
    }
}

