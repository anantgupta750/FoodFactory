package com.example.foodfactory.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodfactory.databinding.FragmentQrBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QrFragment : Fragment() {
    private var _binding: FragmentQrBinding? = null
    private val binding get() = _binding!!
    private lateinit var edata:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQrBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!edata.isEmpty())
        {
           val writer = QRCodeWriter()
           try
           {
               val bitMatrix = writer.encode(edata, BarcodeFormat.QR_CODE, 512, 512)
               val width = bitMatrix.width
               val height = bitMatrix.height
               val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
               for (x in 0 until width) {
                   for (y in 0 until height) {
                       bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                   }
               }
               binding.ivQRCode.setImageBitmap(bmp)
           }catch(e:WriterException){
               e.printStackTrace()
           }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
