import { configureStore } from '@reduxjs/toolkit'
import authReducer from './AuthReducer'
import entityDataReducer from './EntityDataReducer'

const store = configureStore({
    reducer: {
        auth: authReducer,
        entity: entityDataReducer
    }
})

export default store