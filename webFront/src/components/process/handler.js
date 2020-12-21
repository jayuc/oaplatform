
const handleFileOne = (one) => {
    if(one){
        let arr = one.split('/');
        if(arr.length > 0){
            return arr[arr.length - 1];
        }
        return null;
    }
    return null;
};

const handleFileList = (formData) => {
    let arr = [];
    let o1 = handleFileOne(formData.fileUrl1);
    let o2 = handleFileOne(formData.fileUrl3);
    let o3 = handleFileOne(formData.fileUrl3);
    if(o1){
        arr.push({name: o1, url: formData.fileUrl1});
    }
    if(o2){
        arr.push({name: o2, url: formData.fileUrl2});
    }
    if(o3){
        arr.push({name: o3, url: formData.fileUrl3});
    }
    return arr;
};

export default {
    handleFileList
}