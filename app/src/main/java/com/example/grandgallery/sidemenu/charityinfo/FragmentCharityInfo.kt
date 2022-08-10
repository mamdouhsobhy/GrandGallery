package com.example.grandgallery.sidemenu.charityinfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.example.grandgallery.core.presentation.base.BaseFragmentBinding
import com.example.grandgallery.databinding.FragmentCharityInfoBinding


class FragmentCharityInfo : BaseFragmentBinding<FragmentCharityInfoBinding>() {

    var txtOne=""
    var txtTwo=""
    var txtThree=""

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtInfo.text="تعتبر جمعية نور الرحمات هي امتداد للأعمال الخيرية التي بدأت بدار روضة نور القرآن. تم تأسيس دار روضة نور القرآن الكريم في شهر يناير عام 2014 وأصبحت تابعة لقطاع المعاهد الأزهرية. تبنى الحافظين والحافظات بدار روضة نور القرآن فكرة القيام بالأعمال الخيرية التطوعية منذ افتتاح الدار جنبا إلى جنب مع حفظ كتاب آيات الذكر الحكيم إيمانا منهم بأن ذلك هو السلوك العملي التطبقي لما يحفظوه من وحي القرآن الكريم. بدأ العمل في المجال الخيري من شباب الدار بأنشطة يسيرة كزيارة دور الأيتام والمسنين، ومساعدة الفقراء والمساكين وذوي الاحتياجات الخاصة، وما إلى ذلك مما تيسر القيام به من أعمال خيرية. ثم أخذ العمل الخيري في الدار شكلا موسعا متخصصا ومدروسا، فبدؤوا بتنظيم حملات كبيرة في مواسم الخير والعطاء في شهر رمضان والأعياد وغيرها من المواسم. وأخيرا وبعدما بدؤا ينظمون مشاريع خيرية كبيرة ومدروسة والتي كان آخرها مشروع كفالة الأيتام قرآنيا وتعليميا وأخلاقيا أيقنوا أنه يجب إقامة مؤسسة مستقلة عن الدار، ليكون لها الحق ومطلق الحرية في إقامة وتنظيم والإشراف على أي عمل خيري. قراءة أقل\n" +
                "\n" +
                "* شعارنا\n" +
                "\n" +
                "\"ارحموا من في الأرض يرحمكم من في السماء\"\n" +
                "من هنا بدأت فكرة إقامة جمعية نور الرحمات الخيرية لتكون نورا لكل محتاج، ورحمة لكل مكلوم ومكروب، لذلك جعلت شعارها حديث رسول الله صلى الله عليه وسلم: \"ارحموا من في الأرض يرحمكم من في السماء\".\n"

    }

}