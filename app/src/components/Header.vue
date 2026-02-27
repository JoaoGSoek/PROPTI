<script setup>
import { CirclePlus } from 'lucide-vue-next';
const props = defineProps(['title', 'addBtnPath']);
</script>

<template>
	<header class="d-flex justify-content-between mb-3 py-3 px-4 bg-primary text-white rounded">
      <h3 class="mb-0">{{props.title}}</h3>
      <RouterLink v-if="props.addBtnPath" :to="props.addBtnPath" class="btn btn-light d-flex align-items-center gap-1">
				<CirclePlus size="18" />
				Adicionar
			</RouterLink>
    </header>
</template>