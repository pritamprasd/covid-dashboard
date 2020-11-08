const intialState = {
    token: ''
}

function authReducer(state = intialState, action) {
    switch (action.type) {
        case 'jwt':
            return {
                ...state,
                token: action.token
            }
        default:
            return state
    }
}

export default authReducer