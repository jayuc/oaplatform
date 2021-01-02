
import Config from '@/config';

const goto = (path) => {
    const menu = Config.get('global_menu');
    if(menu){
        menu.open(path);
    }
};

export default {
    goto
}