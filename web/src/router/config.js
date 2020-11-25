import TabsView from '@/layouts/tabs/TabsView'
import BlankView from '@/layouts/BlankView'
import PageView from '@/layouts/PageView'

// 路由配置
const options = {
  routes: [
    {
      path: '/login',
      name: '登录页',
      component: () => import('@/pages/login')
    },
    {
      path: '*',
      name: '404',
      component: () => import('@/pages/exception/404'),
    },
    {
      path: '/403',
      name: '403',
      component: () => import('@/pages/exception/403'),
    },
    {
      path: '/',
      name: '首页',
      component: TabsView,
      redirect: '/login',
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          meta: {
            icon: 'dashboard'
          },
          component: BlankView,
          children: [
            {
              path: 'workplace',
              name: '工作台',
              meta: {
                page: {
                  closable: false
                }
              },
              component: () => import('@/pages/dashboard/workplace'),
            },
            {
              path: 'analysis',
              name: '分析页',
              component: () => import('@/pages/dashboard/analysis'),
            }
          ]
        },
        {
          path: 'datascreen',
          name: '数据大屏',
          meta: {
            icon: 'fund'
          },
          redirect: '/datascreen/index',
          component: BlankView,
          children: [{
            path: 'index',
            name: '数据大屏',
            meta: {
              invisible: true
            },
            component: () => import('@/pages/dashboard/datascreen'),
          }]
        },
        {
          path: 'projectmgmt',
          name: '项目管理',
          meta: {
            icon: 'project'
          },
          redirect: '/projectmgmt/list',
          component: PageView,
          children: [
            {
              path: 'list',
              name: '项目列表',
              meta: {
                icon: 'project',
                invisible: true
              },
              component: () => import('@/pages/projectMgmt'),
            }
          ]
        },
        {
          path: 'collaboration',
          name: '专业协同',
          meta: {
            icon: 'reconciliation'
          },
          redirect: '/collaboration/taskmgmt',
          component: PageView,
          children: [
            {
              path: 'taskmgmt',
              name: '任务管理',
              meta: {
                icon: 'reconciliation',
                invisible: true
              },
              component: () => import('@/pages/collaboration'),
            }
          ]
        },
        {
          path: 'staffworkinfo',
          name: '人员工作信息',
          meta: {
            icon: 'solution'
          },
          component: PageView
        },
        {
          path: 'personnelmgmt',
          name: '人员管理',
          meta: {
            icon: 'team'
          },
          component: PageView
        },
        {
          path: 'warehousemgmt',
          name: '物资管理',
          meta: {
            icon: 'home'
          },
          redirect: '/warehousemgmt/list',
          component: PageView,
          children: [
            {
              path: 'list',
              name: '物资列表',
              meta: {
                // icon: 'project',
              },
              component: () => import('@/pages/warehouseMgmt'),
            }
          ]
        },
        {
          path: 'form',
          name: '表单页',
          meta: {
            icon: 'form',
            invisible: true
          },
          component: PageView,
          children: [
            {
              path: 'basic',
              name: '基础表单',
              component: () => import('@/pages/form/basic'),
            },
            {
              path: 'step',
              name: '分步表单',
              component: () => import('@/pages/form/step'),
            },
            {
              path: 'advance',
              name: '高级表单',
              component: () => import('@/pages/form/advance'),
            }
          ]
        },
        {
          path: 'list',
          name: '列表页',
          meta: {
            icon: 'table',
            invisible: true
          },
          component: PageView,
          children: [
            {
              path: 'query',
              name: '查询表格',
              meta: {
                authority: 'queryForm',
              },
              component: () => import('@/pages/list/QueryList'),
            },
            {
              path: 'primary',
              name: '标准列表',
              component: () => import('@/pages/list/StandardList'),
            },
            {
              path: 'card',
              name: '卡片列表',
              component: () => import('@/pages/list/CardList'),
            },
            {
              path: 'search',
              name: '搜索列表',
              component: () => import('@/pages/list/search/SearchLayout'),
              children: [
                {
                  path: 'article',
                  name: '文章',
                  component: () => import('@/pages/list/search/ArticleList'),
                },
                {
                  path: 'application',
                  name: '应用',
                  component: () => import('@/pages/list/search/ApplicationList'),
                },
                {
                  path: 'project',
                  name: '项目',
                  component: () => import('@/pages/list/search/ProjectList'),
                }
              ]
            }
          ]
        },
        {
          path: 'details',
          name: '详情页',
          meta: {
            icon: 'profile',
            invisible: true
          },
          component: BlankView,
          children: [
            {
              path: 'basic',
              name: '基础详情页',
              component: () => import('@/pages/detail/BasicDetail')
            },
            {
              path: 'advance',
              name: '高级详情页',
              component: () => import('@/pages/detail/AdvancedDetail')
            }
          ]
        },
        {
          path: 'result',
          name: '结果页',
          meta: {
            icon: 'check-circle-o',
            invisible: true
          },
          component: PageView,
          children: [
            {
              path: 'success',
              name: '成功',
              component: () => import('@/pages/result/Success')
            },
            {
              path: 'error',
              name: '失败',
              component: () => import('@/pages/result/Error')
            }
          ]
        },
        {
          path: 'exception',
          name: '异常页',
          meta: {
            icon: 'warning',
            invisible: true
          },
          component: BlankView,
          children: [
            {
              path: '404',
              name: 'Exp404',
              component: () => import('@/pages/exception/404')
            },
            {
              path: '403',
              name: 'Exp403',
              component: () => import('@/pages/exception/403')
            },
            {
              path: '500',
              name: 'Exp500',
              component: () => import('@/pages/exception/500')
            }
          ]
        },
        {
          path: 'components',
          name: '内置组件',
          meta: {
            icon: 'appstore-o',
            invisible: true
          },
          component: PageView,
          children: [
            {
              path: 'taskCard',
              name: '任务卡片',
              component: () => import('@/pages/components/TaskCard')
            },
            {
              path: 'palette',
              name: '颜色复选框',
              component: () => import('@/pages/components/Palette')
            },
            {
              path: 'table',
              name: '高级表格',
              component: () => import('@/pages/components/table')
            }
          ]
        },
        {
          name: '验权表单',
          path: 'auth/form',
          meta: {
            icon: 'file-excel',
            authority: {
              permission: 'form'
            }
          },
          component: () => import('@/pages/form/basic')
        }
      ]
    },
    {
      path: '/largedatascreen',
      name: '数据可视化系统',
      component: () => import('@/pages/largeDataScreen')
    },
    {
      path: '/projectdatascreen',
      name: '项目信息可视化',
      component: () => import('@/pages/largeDataScreen/ProjectDataScreen')
    },
    {
      path: '/businessAnalysis',
      name: '公司经营分析系统',
      component: () => import('@/pages/largeDataScreen/businessAnalysis')
    },
    {
      path: '/humanresources',
      name: '公司人力资源分析系统',
      component: () => import('@/pages/largeDataScreen/humanResources')
    },
    {
      path: '/financialanalysis',
      name: '公司财务分析系统',
      component: () => import('@/pages/largeDataScreen/financialAnalysis')
    }
  ]
}

export default options
