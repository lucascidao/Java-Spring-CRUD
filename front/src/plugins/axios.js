import ax from 'axios'

    /* eslint-disable */

export const axios = ax

export default {
    install (Vue, options) {
        Vue.prototype.$axios = ax
    }
}