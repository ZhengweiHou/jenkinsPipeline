function display_usage() {
    echo "usage: $0 [-f jenkins pck list file] [-d target documents] "
    echo "f - jenkins pck list file"
    echo "d - target documents"
}


while getopts ":f:d:" OPT
do
    case $OPT in
        f) jenkins_pck_list_file="${OPTARG}"
           ;;
        d) target_documents="${OPTARG}"
	   ;;
        ?) echo "invalid option -- ${OPTARG}"
           display_usage
           exit 1
           ;;
        *) ;;
    esac
done

LIB_PATH=lib/
[ "#${target_documents}#" != "##" ] && LIB_PATH=${target_documents}

# ALL_LST文件中配置所有进程包，没有配置的包将不会被拉取，内容mode groupId artifactId
ALL_LST=all.lst 

if [ ! -f "${ALL_LST}" ]
then
	echo "can not find all module list file, check on [`hostname`] please!!!"
	echo "说明：all module list文件中配置所有进程包，没有配置的包将不会被拉取，内容:mode groupId artifactId"
	exit 1
fi

ALL_LST=$(readlink -f "${ALL_LST}")


# 将jenkins输出的list文件组装成拉包需要的list
j_version=""
echo -n '' > $$.tmp

sed -i '/^\s*$/d' $jenkins_pck_list_file

while read groupId artifactId version
do
	mode=''
	mode=$(grep -v -e '^#' -e '^\s*$' ${ALL_LST} | grep "$groupId\s*$artifactId"| awk '{print $1}')
	if [ "#$mode#" == "##" ]
	then
		continue
		#mode=${artifactId%%-*}
	fi
	echo -e "${mode}\t${groupId}\t${artifactId}" >> $$.tmp

#	echo   -e "${artifactId%%-*}\t${groupId}\t${artifactId}" >> $$.tmp 
	j_version=${version}
	
done < $jenkins_pck_list_file

if [ "#${j_version}#" == "##" ]
then
	echo "没有包将被拉取，如有疑问，请检查主机[`hostname`]文件:${ALL_LST}"
	rm $$.tmp
	exit 0
fi
 
echo "=====拉取:version[${j_version}], 目标目录：[${LIB_PATH}]====="
cat $$.tmp
echo "=================================================="

echo "sh pck.sh -d $LIB_PATH -f $$.tmp $j_version"
bash pck.sh -d $LIB_PATH -f $$.tmp $j_version

rm $$.tmp
