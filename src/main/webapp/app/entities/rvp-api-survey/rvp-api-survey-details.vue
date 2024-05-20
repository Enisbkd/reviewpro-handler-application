<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rvpApiSurvey">
        <h2 class="jh-entity-heading" data-cy="rvpApiSurveyDetailsHeading">
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.detail.title')"></span> {{ rvpApiSurvey.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.overallScoreEnabled')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiSurvey.overallScoreEnabled }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.languages')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiSurvey.languages }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.outOf')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiSurvey.outOf }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.name')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiSurvey.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.active')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiSurvey.active }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.pids')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiSurvey.pids }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.primary')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiSurvey.primary }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rvpApiSurvey.id"
          :to="{ name: 'RvpApiSurveyEdit', params: { rvpApiSurveyId: rvpApiSurvey.id } }"
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

<script lang="ts" src="./rvp-api-survey-details.component.ts"></script>
