<template>
  <form @submit.prevent="onCalculate">
    <div>
      <label>Jährliche Kilometerleistung</label>
      <input type="number" v-model.number="mileage" min="0" required />
    </div>
    <div>
      <label>Fahrzeugtyp</label>
      <select v-model="vehicleType">
        <option>PKW_SMALL</option>
        <option>PKW_MEDIUM</option>
        <option>SUV</option>
        <option>MOTORRAD</option>
      </select>
    </div>
    <div>
      <label>Postleitzahl</label>
      <input type="text" v-model="postcode" required />
    </div>
    <div style="margin-top:8px">
      <button type="submit">Calculate</button>
      <button type="button" @click="onCreate">Create (persist)</button>
    </div>

    <div v-if="result" style="margin-top:12px">
      <h3>Ergebnis</h3>
      <div>Premium: {{ result.premium }}</div>
      <div>Region: {{ result.region }}</div>
      <div>Faktoren: M={{ result.mileageFactor }}, V={{ result.vehicleFactor }}, R={{ result.regionFactor }}</div>
    </div>
  </form>
</template>

<script setup>
import { ref } from 'vue'

const mileage = ref(12000)
const vehicleType = ref('SUV')
const postcode = ref('79189')
const result = ref(null)

async function onCalculate() {
  const res = await fetch('/api/calculate', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ mileage: mileage.value, vehicleType: vehicleType.value, postcode: postcode.value })
  })
  result.value = await res.json()
}

async function onCreate() {
  const res = await fetch('/api/applications', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ mileage: mileage.value, vehicleType: vehicleType.value, postcode: postcode.value })
  })
  result.value = await res.json()
}
</script>

<style scoped>
label { display:block; margin-top:8px; font-weight:600 }
input, select { width:100%; padding:6px; margin-top:4px }
button { margin-right:8px }
</style>
