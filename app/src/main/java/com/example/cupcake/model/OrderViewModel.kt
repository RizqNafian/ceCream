/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Harga ukuran cup */
private const val PRICE_SIZE= 2.00

/** harga pengiriman */
private const val PRICE_FOR_DELIVERY = 3.00

/**
 * kelas OrderViewModel yang digunakan sebagi model (fungsi) untuk tampilan
 */
class OrderViewModel : ViewModel() {

    // mendeklarasikan Ukuran wadah Ice Cream
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    // mendeklarasikan Rasa Ice Cream
    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    // mendeklarasikan cara pengambilan
    private val _pickup = MutableLiveData<String>()
    val pickup: LiveData<String> = _pickup

    // mendeklarasikan harga
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        // mengatur format angka sehigga menyerupai mata uang
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        // menginisial nilai pesanan
        resetOrder()
    }

    /**
     * Mengatur nilai quantity (cup)
     */
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    /**
     * mengatur nilai rasa
     */
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    /**
     * mengatur nilai pengantaran
     */
    fun setPickup(pick: String) {
        _pickup.value = pick
        updatePrice()
    }

    /**
     * mengatur nilai awal rasa dan pengantaran
     */
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }
    fun hasNoPickupSet(): Boolean {
        return _pickup.value.isNullOrEmpty()
    }

    /**
     * mengatur nilai cup untuk sumarry
     */
    fun cup(): String {
        if (_quantity.value == 1){
            return "Cone"
        }else if (_quantity.value == 2){
            return "Cup"
        }else {
            return "Big Cup"
        }
    }

    /**
     * mereset nilai menjadi 0 atau nilai awal
     */
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _pickup.value = ""
        _price.value = 0.0
    }

    /**
     * memperbarui nilai dari harga
     */
    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_SIZE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (_pickup.value == "Delivery") {
            calculatedPrice += PRICE_FOR_DELIVERY
        }
        _price.value = calculatedPrice
    }

}