package com.example.grandgallery.sidemenu.tamrainfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.databinding.FragmentTamraInfoBinding

class FragmentTamraInfo : BaseFragmentBinding<FragmentTamraInfoBinding>() {

    var txt =""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtInfo.text="لتطبيق هو تفعيل لمشاريع  الإطعام  التابعة  لجمعية (نور الرحمات) ، هو تطبيق غير ربحي  يقدم خدمة  للمستفيدين بأنواعهم  من خلال توصيل الوجبات الجاهزة و المواد الغذائية والسلع الحيوية الأخرى .\n" +
                " يعمل التطبيق كوصلة خير بين المتبرع ( المطاعم ، الفنادق، فاعلي الخير، قاعات الأفراح، الجمعيات الخيرية ) ، و المستفيد (الفقراء، المؤسسات الخيرية ، المشردين فى الشوارع، المرضى الغير قادرين على العمل، المرأة المعيلة لأطفال، مؤسسة الرفق بالحيوان ) وذلك عن طريق المتطوعين لفعل الخير ؛ للقضاء على الجوع للأنسان والحيوان  من خلال استغلال الوجبات الزائدة عن احتياجات الأفراد والكيانات المختلفة"

    }


}