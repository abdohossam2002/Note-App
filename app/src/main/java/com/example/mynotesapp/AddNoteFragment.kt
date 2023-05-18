package com.example.mynotesapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_note.*

@Suppress("DEPRECATION")
class AddNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }
    var id:Int?=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buAddNotes.setOnClickListener {
            addNotesEvent()
        }
        setHasOptionsMenu(true)
        id = arguments?.getInt("ID")
        if (id != 0) {
            val title = arguments?.getString("Title")
            etTitle.setText(title)
            val description = arguments?.getString("Description")
            etDesciption.setText(description)
        }
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun addNotesEvent(){
        val title=etTitle.text.toString()
        val description=etDesciption.text.toString()

        val values = ContentValues()
        values.put("Title",title)
        values.put("Description",description)

        val dbManager=DbManager(this!!.activity!!)
            if (id !=0){
                val selectionArgs= arrayOf(id.toString())
                val id =  dbManager.update(values,"ID=?",selectionArgs)
                if (id>0){
                    Toast.makeText(this!!.activity!!,"Record is Update",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this!!.activity!!," Fail To Update Record",Toast.LENGTH_LONG).show()
                }

            }else{
                val id =  dbManager.insertNote(values)
                if (id>0){
                    Toast.makeText(this!!.activity!!,"Record is Added",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this!!.activity!!," Fail To Add Record",Toast.LENGTH_LONG).show()
                }
            }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addnotes_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.backBtn -> {
                view?.findNavController()
                    ?.navigate(R.id.notesListFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}