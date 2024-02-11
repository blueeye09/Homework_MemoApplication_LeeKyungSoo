package kr.co.lion.homework_memoapplication_leekyungsoo

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Memo(): Parcelable {
    // 제목
    var title = ""
    // 내용
    var content = ""
    // 날짜를 담음
    var date = ""

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()!!
        content = parcel.readString()!!
        date = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Memo> {
        override fun createFromParcel(parcel: Parcel): Memo {
            return Memo(parcel)
        }

        override fun newArray(size: Int): Array<Memo?> {
            return arrayOfNulls(size)
        }
    }
}