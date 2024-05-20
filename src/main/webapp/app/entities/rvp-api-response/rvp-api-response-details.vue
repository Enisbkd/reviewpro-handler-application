<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rvpApiResponse">
        <h2 class="jh-entity-heading" data-cy="rvpApiResponseDetailsHeading">
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.detail.title')"></span> {{ rvpApiResponse.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.surveyId')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiResponse.surveyId }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.lodgingId')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiResponse.lodgingId }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.date')"></span>
          </dt>
          <dd>
            <span v-if="rvpApiResponse.date">{{ formatDateLong(rvpApiResponse.date) }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.overallsatsifaction')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiResponse.overallsatsifaction }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.customScore')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiResponse.customScore }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.plantorevisit')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiResponse.plantorevisit }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rvpApiResponse.id"
          :to="{ name: 'RvpApiResponseEdit', params: { rvpApiResponseId: rvpApiResponse.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rvp-api-response-details.component.ts"></script>
