
const isBlank = (str) => {
    if(typeof str === 'string' && str.length === 0){
        return true;
    }
    return false;
};

export default {
    isBlank
}