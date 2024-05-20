<template>
  <div>
    <b-dropdown-item to="/rvp-apilodging">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rvpApilodging')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/rvp-api-response">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rvpApiResponse')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/rvp-api-lodging-cqi">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rvpApiLodgingCqi')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/rvp-api-global-review">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rvpApiGlobalReview')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/rvp-api-lodging-score">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rvpApiLodgingScore')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/rvp-api-survey">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rvpApiSurvey')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
