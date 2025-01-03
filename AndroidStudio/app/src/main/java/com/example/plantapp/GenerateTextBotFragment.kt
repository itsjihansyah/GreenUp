package com.example.plantapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.example.plantapp.databinding.FragmentGenerateTextBotBinding
import com.example.plantapp.databinding.FragmentMyPlantDetailBinding

class GenerateTextBotFragment : Fragment(R.layout.fragment_generate_text_bot) {
    private val API_KEY = "AIzaSyBkjo9f4rygnu0-e-rLbNpRKiJfqdtqNkY"
    private lateinit var chat: Chat
    private lateinit var chatAdapter: RvBotAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextInput: EditText
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var sharedPreferences: SharedPreferences

    private var _binding: FragmentGenerateTextBotBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGenerateTextBotBinding.bind(view)

        sharedPreferences = requireActivity().getSharedPreferences(
            "MySharedPreferences",
            Context.MODE_PRIVATE
        )

        binding.leftIcon.setOnClickListener {
            findNavController().navigate(R.id.action_generateTextBotFragment_to_homeFragment)
        }

        drawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        navigationView = requireActivity().findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { item ->
            onNavigationItemSelected(item)
        }

        // Use ImageView as the menu icon
        val menuIcon: ImageView = view.findViewById(R.id.toolbar) // Make sure this is an ImageView in your XML
        menuIcon.setOnClickListener {
            // Open or close the drawer manually when the ImageView is clicked
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatAdapter = RvBotAdapter()
        recyclerView.adapter = chatAdapter

        editTextInput = view.findViewById(R.id.editTextInput)

        startNewChat()

        val btnSend = view.findViewById<AppCompatImageView>(R.id.btnSend)
        btnSend.setOnClickListener { buttonSendChat() }

        val menu = navigationView.menu
        menu.removeGroup(R.id.group_search_history)

        binding.btnSend.isEnabled = false
        binding.btnSend.setColorFilter(requireContext().getColor(R.color.light_grey2)) // Assuming gray is your disabled color

        binding.editTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val isNotEmpty = !s.isNullOrEmpty()
                binding.btnSend.isEnabled = isNotEmpty
                if (isNotEmpty) {
                    binding.btnSend.setColorFilter(requireContext().getColor(R.color.black)) // Replace with your active color
                } else {
                    binding.btnSend.setColorFilter(requireContext().getColor(R.color.light_grey2)) // Disabled color
                }
            }
        })
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_new_chat -> startNewChat()
            R.id.nav_clear_history -> clearSearchHistory()
            R.id.nav_history -> showSearchHistory()
            else -> {
                val searchQuery = item.title.toString()
                addChatToAdapter(searchQuery, true)

                MainScope().launch {
                    val result = chat.sendMessage(searchQuery)
                    result.text?.let { addChatToAdapter(it, false) }
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

    private fun startNewChat() {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = API_KEY
        )

        // Initialize chat without predefined messages
        chat = generativeModel.startChat()

        // Clear the adapter to start with a fresh chat
        chatAdapter.clear()

        drawerLayout.closeDrawer(GravityCompat.END)
    }

    fun buttonSendChat() {
        val userMessage = editTextInput.text.toString()
        addChatToAdapter(userMessage, true)

        val generatingMessage = "generating..."
        addChatToAdapter(generatingMessage, false)

        editTextInput.setText("")

        MainScope().launch {
            val result = chat.sendMessage(userMessage)
            result.text?.let {
                replaceGeneratingWithResponse(it)
            }
            saveSearchToHistory(userMessage)
        }
    }

    private fun replaceGeneratingWithResponse(responseText: String) {
        // Find the last message with "generating..."
        for (i in chatAdapter.getChatList().size - 1 downTo 0) {
            val chatItem = chatAdapter.getChatList()[i]
            if (chatItem.message == "generating...") {
                // Replace it with the actual response
                chatItem.message = styleMessage(responseText)
                chatAdapter.notifyItemChanged(i)
                break
            }
        }
    }

    private fun addChatToAdapter(message: String, isUser: Boolean) {
        val styledMessage = styleMessage(message)
        val chatItem = BotModal(styledMessage, isUser)
        chatAdapter.addChat(chatItem)
        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
    }

    private fun saveSearchToHistory(search: String) {
        if (search.trim().isNotEmpty()) {
            val currentHistory = retrieveSearchHistory(requireContext()).toMutableList()
            currentHistory.add(search)

            val historyString = currentHistory.joinToString(",")
            val editor = sharedPreferences.edit()
            editor.putString("search_history", historyString)
            editor.apply()
        }
    }

    private fun showSearchHistory() {
        val searchHistoryList = retrieveSearchHistory(requireContext())
        val menu = navigationView.menu

        for ((index, searchQuery) in searchHistoryList.withIndex()) {
            val menuItem = menu.add(R.id.group_search_history, Menu.NONE, index, searchQuery)
            menuItem.setOnMenuItemClickListener { menuItem ->
                drawerLayout.closeDrawer(GravityCompat.END)
                val userMessage = menuItem.title.toString()
                addChatToAdapter(userMessage, true)

                MainScope().launch {
                    val result = chat.sendMessage(userMessage)
                    result.text?.let { addChatToAdapter(it, false) }
                }

                true
            }
        }
    }

    private fun clearSearchHistory() {
        val editor = sharedPreferences.edit()
        editor.putString("search_history", "")
        editor.apply()
        val menu = navigationView.menu
        menu.removeGroup(R.id.group_search_history)
        chatAdapter.clear()
        drawerLayout.closeDrawer(GravityCompat.END)
    }

    private fun retrieveSearchHistory(context: Context): List<String> {
        val searchHistoryString: String? = sharedPreferences.getString("search_history", "")
        return searchHistoryString?.split(",")?.filterNotNull()?.filter { it.trim().isNotEmpty() }?.toList() ?: emptyList()
    }

    private fun styleMessage(message: String): String {
        // Remove *text* markers for headlines
        val withoutHeadlineMarkers = message.replace(Regex("""\*(.*?)\*""")) { matchResult ->
            matchResult.groupValues[1] // Keep only the text inside the markers
        }

        // Replace * text (bullet points) with a bullet symbol
        val withBullets = withoutHeadlineMarkers.replace(Regex("""^\*\s""", RegexOption.MULTILINE)) {
            "• " // Replace with a bullet
        }

        return withBullets
    }
}

