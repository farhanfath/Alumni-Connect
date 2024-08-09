package dts.pnj.farhan.view.home.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dts.pnj.farhan.databinding.FragmentProfileBinding
import dts.pnj.farhan.utils.ViewModelFactory
import dts.pnj.farhan.utils.viewmodel.MainViewModel
import dts.pnj.farhan.view.auth.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.name.isEmpty()) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                binding.profileName.text = user.name
                binding.profileNim.text = user.nim
                binding.profileEmail.text = user.email
                binding.profileClass.text = user.className
            }
        }

        binding.logoutBtn.setOnClickListener {
            showLogoutConfirmationDialog()
        }

    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { dialog, which ->
                confirmLogout()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun confirmLogout() {
        viewModel.logout()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}