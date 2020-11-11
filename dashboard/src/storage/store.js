import { configureStore } from '@reduxjs/toolkit'
import authReducer from './AuthReducer'

const store = configureStore({
    reducer: {
        auth: authReducer
    }
})

export default store