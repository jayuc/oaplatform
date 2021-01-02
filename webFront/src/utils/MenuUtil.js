
import Config from '@/config';

const goto = (path, index) => {
    const menu = Config.get('global_menu');
    if(menu){
        menu.open(path);
        menu.activeNode = index;
    }
};

export default {
    goto
}