const intialState = {
    currentEntity: {}
}

function entityDataReducer(state = intialState, action) {
    switch (action.type) {
        case 'currentEntityData':
            return {
                ...state,
                currentEntity: action.currentEntityData
            }
        default:
            return state
    }
}

export default entityDataReducer