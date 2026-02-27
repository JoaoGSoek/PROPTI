<script setup>
import { RouterLink, RouterView } from "vue-router";
import { Factory, Trees, Scroll } from 'lucide-vue-next'
</script>

<template>
  <div class="d-flex" id="wrapper">
    <!-- Sidebar -->
    <div class="bg-light border-end" style="min-width: 250px; min-height: 100vh;">
      <div class="sidebar-heading p-3">
				<p class="mb-0 text-uppercase fw-bold fs-3 text-primary">Propti</p>
			</div>
      <div class="list-group list-group-flush">
        <RouterLink to="/" class="list-group-item list-group-item-action d-flex align-items-center gap-1">
					<Factory size="18" />
					<p class="mb-0">Otimização (Home)</p>
				</RouterLink>
        <RouterLink to="/materials" class="list-group-item list-group-item-action d-flex align-items-center gap-1">
					<Trees size="18" />
					<p class="mb-0">Matérias Primas</p>
				</RouterLink>
        <RouterLink to="/products" class="list-group-item list-group-item-action d-flex align-items-center gap-1">
					<Scroll size="18" />
					<p class="mb-0">Produtos</p>
				</RouterLink>
      </div>
    </div>

    <!-- Conteúdo da Página -->
    <div id="page-content-wrapper" class="w-100 p-4">
      <RouterView />
    </div>
  </div>
</template>